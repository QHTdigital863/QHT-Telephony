package com.qht.crm;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.qht.crm.ami.TaskScheduler.CustEmpCampUpdatePeriodicRunnable;
import com.qht.crm.ami.TaskScheduler.DeletedPreviousChustomerPageRunnable;
import com.qht.crm.ami.TaskScheduler.HardInsertCallDetailAndCostRunnable;
import com.qht.crm.ami.TaskScheduler.StartedCampaignRunDataFlushRunnable;
import com.qht.crm.data.TrackedSchduledJobs;
import com.qht.crm.service.CampaignRunTrackingService;
import com.qht.crm.ami.service.SaveMemoryDataToDatabaseService;
import com.qht.crm.repository.CallDetailRepository;
import com.qht.crm.repository.OrganizationRepository;
import com.qht.crm.service.CustomerPropertyInventoryService;
import com.qht.crm.service.CustomerService;
import com.qht.crm.whatsapp.repository.WhatsAppChatHistoryRepository;
import com.qht.crm.whatsapp.repository.WhatsAppFlattenMessageRepository;
import com.qht.crm.whatsapp.repository.WhatsAppNumberReportRepository;
import com.qht.crm.whatsapp.service.WhatsAppChatHistoryService;
import com.qht.crm.whatsapp.taskscheduler.*;

@Component
@Lazy(false)
@RequiredArgsConstructor
public class GracefulShutdownFlush {

    private final SaveMemoryDataToDatabaseService campaignDataToDatabaseService;
    private final OrganizationRepository organizationRepository;

    private final CallDetailRepository callDetailRepository;
    private final CampaignRunTrackingService campaignRunTrackingService;

    private final WhatsAppChatHistoryService whatsAppChatHistoryService;
    private final WhatsAppChatHistoryRepository whatsAppChatHistoryRepository;
    private final WhatsAppNumberReportRepository whatsAppNumberReportRepository;
    private final WhatsAppFlattenMessageRepository whatsAppFlattenMessageRepository;

    private final CustomerService customerService;
    private final CustomerPropertyInventoryService customerPropertyInventoryService;

    private volatile boolean executed = false;

    @EventListener(ContextClosedEvent.class)
    public void onContextClosed(ContextClosedEvent event) {
        // Prevent double execution in weird shutdown paths
        if (executed) return;
        executed = true;

        System.out.println("GracefulShutdownFlush: starting final flush before bean destruction");

        // 1) Flush campaign/customer memory -> DB
        try {
            CustEmpCampUpdatePeriodicRunnable r = new CustEmpCampUpdatePeriodicRunnable();
            r.setCampaignDataToDatabaseService(campaignDataToDatabaseService);
            r.setJobId(TrackedSchduledJobs.custEmpCampUpdatePeriodicRunnable);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2) Deleted page cleanup
        try {
            DeletedPreviousChustomerPageRunnable r = new DeletedPreviousChustomerPageRunnable();
            r.setJobId(TrackedSchduledJobs.deletedPreviousChustomerPageRunnable);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3) Org data save
        try {
            com.qht.crm.TaskScheduler.SaveOrganizationDataRunnable r =
                    new com.qht.crm.TaskScheduler.SaveOrganizationDataRunnable();
            r.setOrganizationRepository(organizationRepository);
            r.setJobId(TrackedSchduledJobs.saveOrganizationDataRunnable);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4) CDR + cost save
        try {
            HardInsertCallDetailAndCostRunnable r = new HardInsertCallDetailAndCostRunnable();
            r.setCallDetailRepository(callDetailRepository);
            r.setJobId(TrackedSchduledJobs.hardInsertCallDetailAndCostRunnableCron);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5) Campaign run flush (force flush)
        try {
            com.qht.crm.data.StartedCampaignData.forceMarkAllFlushDue();

            StartedCampaignRunDataFlushRunnable r = new StartedCampaignRunDataFlushRunnable();
            r.setJobId(TrackedSchduledJobs.startedCampaignRunDataFlushRunnable);
            r.setTrackingService(campaignRunTrackingService);
            r.setForceFlush(true);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 6) WhatsApp writes
        try {
            WhatsAppHardInsertChatHistoryRunnable r = new WhatsAppHardInsertChatHistoryRunnable();
            r.setWhatsAppChatHistoryService(whatsAppChatHistoryService);
            r.setWhatsAppChatHistoryRepository(whatsAppChatHistoryRepository);
            r.setJobId(TrackedSchduledJobs.whatsAppHardInsertChatHistoryRunnable);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WhatsAppHardInsertReportDataRunnable r = new WhatsAppHardInsertReportDataRunnable();
            r.setWhatsAppNumberReportRepository(whatsAppNumberReportRepository);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WhatsAppHardInsertChatHistoryUpdatesRunnable r = new WhatsAppHardInsertChatHistoryUpdatesRunnable();
            r.setWhatsAppChatHistoryRepository(whatsAppChatHistoryRepository);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WhatsAppHardInsertFlattenMessageRunnable r = new WhatsAppHardInsertFlattenMessageRunnable();
            r.setWhatsAppFlattenMessageRepository(whatsAppFlattenMessageRepository);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 7) Save customer flags + inventory
        try {
            WhatsAppSaveAllCustomerDataRunnable r = new WhatsAppSaveAllCustomerDataRunnable();
            r.setJobId(TrackedSchduledJobs.whatsAppSaveAllCustomerDataRunnable);
            r.setCustomerService(customerService);   // IMPORTANT: not null
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WhatsAppSaveCustomerPropertyInventoryRunnable r = new WhatsAppSaveCustomerPropertyInventoryRunnable();
            r.setJobId(TrackedSchduledJobs.whatsAppSaveCustomerPropertyInventoryRunnable);
            r.setCustomerPropertyInventoryService(customerPropertyInventoryService);
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("GracefulShutdownFlush: completed");
    }
}

import { Component, OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';
import { ConstantsService } from '../service/constants/constants.service';
import { MenuDataService } from '../service/menu-data/menu-data.service';
import { MENU_ITEMS } from './pages-menu';

@Component({
  selector: 'ngx-pages',
  styleUrls: ['pages.component.scss'],
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="menuDataService.menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class PagesComponent implements OnInit {

  menu:NbMenuItem[]=[];

  constructor( protected menuDataService: MenuDataService)
  {
  }

  ngOnInit(){
    // Set the full menu synchronously before <nb-menu> renders so the sidebar
    // always shows (org menuAccess can be empty "[]" for onboarded orgs, and
    // <nb-menu> does not reliably react to a later async menu update).
    this.menuDataService.menu = MENU_ITEMS;
  }

}



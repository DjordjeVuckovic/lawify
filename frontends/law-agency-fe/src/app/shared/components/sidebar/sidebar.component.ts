import {Component, EventEmitter, HostListener, OnInit, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {navbarData} from "./nav-data";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

interface SideNavToggle {
  screenWidth: number;
  collapsed: boolean;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLinkActive, RouterLink, FaIconComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {
  @Output() onToggleSideNav: EventEmitter<SideNavToggle> = new EventEmitter();
  screenWidth = 0;
  collapsed = false;
  notificationNum = 0;
  navData = navbarData;
  /* userToken:UserToken;*/
  isLoggedIn: boolean = false;

  ngOnInit(): void {
    this.screenWidth = window.innerWidth;
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth <= 2133) {
      this.collapsed = false;
      this.onToggleSideNav.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});
    }
  }

  closeSidenav(): void {
    this.collapsed = false;
    this.onToggleSideNav.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});
  }

  toggleCollapse(): void {
    this.collapsed = !this.collapsed;
    this.onToggleSideNav.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});
  }

  openSidenav(): void {
    if (!this.collapsed) {
      this.collapsed = true;
      this.onToggleSideNav.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});
    }
  }

}

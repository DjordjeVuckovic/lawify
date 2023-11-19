import {Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {BodyComponent} from "./shared/components/body/body.component";
import {SidebarComponent} from "./shared/components/sidebar/sidebar.component";
import {HttpClientModule} from "@angular/common/http";
interface SideNavToggle{
  screenWidth: number;
  collapsed: boolean;
}
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HttpClientModule,
    BodyComponent,
    SidebarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  isSideNavCollapsed = false;
  screenWidth = 0;
  onToggleSideNav(data: SideNavToggle):void {
    this.screenWidth = data.screenWidth;
    this.isSideNavCollapsed = data.collapsed;
  }
}

import {Component, Input, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-body',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './body.component.html',
  styleUrl: './body.component.scss'
})
export class BodyComponent implements OnInit{
  @Input() collapsed =true;
  @Input() screenWidth = 0;
  ngOnInit(): void {
  }
  getBodyClass(): string{
    let styleClass = '';
    if(this.collapsed && this.screenWidth > 2133){
      styleClass = 'body-trimmed;'
    }
    else if(this.collapsed && this.screenWidth <= 2133 && this.screenWidth > 0){
      styleClass = 'body-md-screen';
    }
    return styleClass;
  }


}

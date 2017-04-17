import {Component, OnInit} from '@angular/core';
import {AuthService} from '../common/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private authService: AuthService) {
  }


  ngOnInit() {
  }


  authenticated(): boolean {
    return this.authService.authenticated();
  }

  public isUser(): boolean {
    return this.authService.hasAuthority('ROLE_USER');
  }


  public roles() {
    return this.authService.authorities();
  }

}

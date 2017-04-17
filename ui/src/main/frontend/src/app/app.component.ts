import {Component} from '@angular/core';
import {AuthService} from './common/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private authService: AuthService, private router: Router) {
  }

  authenticated(): boolean {
    return this.authService.authenticated();
  }

  public isUser(): boolean {
    return this.authService.hasAuthority('ROLE_USER');
  }

  public isAdmin(): boolean {
    return this.authService.hasAuthority('ROLE_ADMIN');
  }

  getUserName() {
    return this.authService.displayName();
  }

  getAuthorities() {
    return this.authService.authorities();
  }

  doLogout() {
    this.authService.logout();
    this.router.navigate(['/index']);
  }

}

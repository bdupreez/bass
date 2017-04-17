/**
 * Created by brian on 2016/07/26.
 */
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import {AuthService} from './auth.service';
import {Injectable} from '@angular/core';

@Injectable()
export class DefaultUrlGuard implements CanActivate {

  constructor(protected router: Router, protected auth: AuthService) {

  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {

    if (this.auth.authenticated()) {
      return true;
    } else {
      this.router.navigate(['/login', {returnUrl: state.url}]);
      return false;
    }

  }
}

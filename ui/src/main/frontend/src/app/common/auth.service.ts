import {Injectable} from '@angular/core';
import {JwtHelper, tokenNotExpired} from 'angular2-jwt';
import {ConfigService, IAppConfig} from './config.service';
import {Headers, Http, RequestOptions} from '@angular/http';
import {Router} from '@angular/router';


export interface LoginCallback {
  onLogin(error: string, result: any): void;
}

@Injectable()
export class AuthService {
  private tokenName = 'id_token';
  private token: string;
  private config: IAppConfig;
  private helper: JwtHelper = new JwtHelper();

  constructor(configService: ConfigService, private http: Http, private router: Router) {
    if (this.authenticated()) {
      this.setToken(localStorage.getItem('id_token'));
    } else {
      localStorage.removeItem('id_token');
    }
    configService.getAppConfig().subscribe(config => {
      this.config = config;
    });
  }

  public login(username: string, password: string, callback: LoginCallback) {
    let headers = new Headers();
    console.log(this.config);
    headers.append('Authorization', 'Basic ' + btoa(`${this.config.clientId}:secret`));
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    let options = new RequestOptions({headers: headers});
    let data = `grant_type=password&username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`;
    this.http.post(`${this.config.authServer}/uaa/oauth/token`, data, options)
      .subscribe(dataReturn => {
          this.setToken(dataReturn.json().access_token, true);
          callback.onLogin(null, dataReturn.json().access_token);
        },
        error => {
          callback.onLogin(error.json(), null);
        });
  }

  public logout() {
    localStorage.removeItem(this.tokenName);
  }

  private setToken(token: string, force = false) {
    if (force) {
      localStorage.setItem(this.tokenName, token);
    }
    try {
      if (tokenNotExpired()) {
        this.token = this.helper.decodeToken(token);
      }
    } catch (e) {/*swallow token not found*/
    }
  }

  public authenticated() {
    return tokenNotExpired();
  }

  public authorities(): string[] {
    if (!this.token) {
      return [];
    }
    return this.token['authorities'];
  }

  public hasAuthority(role) {
    return this.authorities().indexOf(role) >= 0;
  }


  public displayName() {
    let display = this.token['displayName'];
    if (!display) {
      display = this.username();
    }
    return display;
  }

  public username() {
    return this.token['user\_name'];
  }


}

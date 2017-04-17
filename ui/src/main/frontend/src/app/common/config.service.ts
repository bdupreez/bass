import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/Rx';
import {environment} from '../../environments/environment';

export interface IAppConfig {
  authServer: string;
  callbackUrl: string;
  implicitGrantUrl: string;
  clientId: string;
  scopes: string;
  resourceServerUrl: string;
  claimsDiagnosticUrl: string;
  frontendUrls: string[];
  securityTokenUrls: string[];
}

@Injectable()
export class ConfigService {

  config: IAppConfig;

  constructor(private http: Http) {
    console.log(environment);
    this.getAppConfig().subscribe(config => {
      this.config = config;
    });
  }


  getAppConfig(): any {
    if (environment.local) {
      return this.http.get('http://localhost:9090/new-config/')
        .map(res => res.json());
    } else {
      return this.http.get('/new-config/')
        .map(res => res.json());
    }
  }

}

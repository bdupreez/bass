import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Http, HttpModule, RequestOptions} from '@angular/http';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {ROUTES} from './app.routes';
import {RouterModule} from '@angular/router';
import {AuthService} from './common/auth.service';
import {ConfigService} from './common/config.service';
import {MainComponent} from './main/main.component';
import {CookieService} from 'angular2-cookie/services/cookies.service';
import {DefaultUrlGuard} from './common/url.guard';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AuthConfig, AuthHttp} from 'angular2-jwt';
import {LoginComponent} from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MainComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule.forRoot(ROUTES, {useHash: true, enableTracing: false}),
    NgbModule.forRoot()
  ],
  providers: [
    AuthService,
    ConfigService,
    CookieService,
    DefaultUrlGuard,
    {
      provide: AuthHttp,
      useFactory: authHttpServiceFactory,
      deps: [Http, RequestOptions]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function authHttpServiceFactory(http: Http, options: RequestOptions) {
  return new AuthHttp(new AuthConfig({}), http, options);
}

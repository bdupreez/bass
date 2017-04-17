import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {MainComponent} from './main/main.component';
import {DefaultUrlGuard} from './common/url.guard';
import {LoginComponent} from './login/login.component';

export const ROUTES: Routes = [
  {path: '', component: HomeComponent},
  {path: 'index', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'main', component: MainComponent, canActivate: [DefaultUrlGuard]},

];

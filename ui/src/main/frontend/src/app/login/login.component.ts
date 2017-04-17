import { Component, OnInit } from '@angular/core';
import {Validators, FormBuilder, FormGroup} from '@angular/forms';
import {Router, ActivatedRoute} from '@angular/router';
import {AuthService} from '../common/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loginForm: FormGroup;
  private returnUrl: string;
  private errorLogin = false;
  private errorMessage: string;

  constructor(private auth: AuthService, private fb: FormBuilder, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.params['returnUrl'] || '/';
    if (this.auth.authenticated()) {
      this.router.navigateByUrl(this.returnUrl);
    }

    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSignin() {
    this.errorLogin = false;
    let value = this.loginForm.value;

    this.auth.login(value.username, value.password, {
      onLogin: (error, success) => {
        if (error) {
          this.errorLogin = true;
          this.errorMessage = error;
        } else {
          if (this.returnUrl) {
            this.router.navigateByUrl(this.returnUrl);
          }
        }
      }
    });
  }
}

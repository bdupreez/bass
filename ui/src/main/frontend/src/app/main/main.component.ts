import {Component, OnInit} from '@angular/core';
import {AuthHttp} from 'angular2-jwt';
import {ConfigService} from '../common/config.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  public result: string;
  ngOnInit() {
  }

  constructor(private configService: ConfigService, private authHttp: AuthHttp) {
  }


  callSecure() {
    this.callService('api/secure/');
  }

  private callService(theUrl: string) {

    this.authHttp.get(this.configService.config.resourceServerUrl + theUrl)
      .map(res => res.json())
      .subscribe(message => {
        this.result = message.result;
      }, err => {
        console.log(err);
      });
  }
}

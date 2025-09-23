import { Component } from '@angular/core';
import { HttpServiceService } from '../http-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  endpoint = "http://localhost:8080/Auth/signUp";

  form:any = {
    error:false,
    message:'',
    data:{id : null},
    inputerror:{},
  }

  constructor(private httpService : HttpServiceService, private router : Router){

  }

  signUp(){
    var self = this;

    this.httpService.post(this.endpoint, this.form.data, function(res: any){

      self.form.message = '';
      self.form.inputerror = {};

      if (res.result.message) {
        self.form.message = res.result.message;
      }

      self.form.error = !res.success;
      if (self.form.error && res.result.inputerror) {
        self.form.inputerror = res.result.inputerror;
      }
    });
  }

  reset(){
   location.reload();
  }
}

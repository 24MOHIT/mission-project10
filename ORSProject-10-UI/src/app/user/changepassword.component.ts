import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpServiceService } from '../http-service.service';

@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html',
})
export class ChangepasswordComponent {

  form: any = {
    error: false,
    message: '',
    data: { id: null },
    inputerror: {},
  };

  constructor(public httpService: HttpServiceService, private route: Router) {
  }

  changePassword() {
    var _self = this;
    this.form.data.loginId = localStorage.getItem("loginId")
    this.httpService.post("http://localhost:8080/User/changePassword", this.form.data, function (res: any) {
      _self.form.message = '';
      _self.form.inputerror = {};
      if (res.success) {
        _self.form.message = res.result.message;
        _self.form.data.id = res.result.data;
      } else {
        _self.form.error = true;
        if (res.result.inputerror) {
          _self.form.inputerror = res.result.inputerror;
        }
        _self.form.message = res.result.message;
      }
    });
  }

  forward(page: any) {
    location.reload();
  }

}

import { Component } from '@angular/core';
import { HttpServiceService } from '../http-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceLocatorService } from '../service-locator.service';
import { BaseCtl } from '../base.component';
import { FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',

})
export class UserComponent extends BaseCtl {

  getKey = false;
  selected = null;
  fileToUpload: any = null;
  userForm: any = null;
  uploadForm: any;


  constructor(public locator: ServiceLocatorService, route: ActivatedRoute, private httpClient: HttpClient) {
    super(locator.endpoints.USER, locator, route);
  }

  onFileSelect(event: Event): void {
    const input = event.target as HTMLInputElement;

    this.fileToUpload = input.files![0];
    console.log(this.fileToUpload);
  }


  onUpload(userform: FormData) {
    this.submit();
    console.log(this.form.data.id + '---- after submit');

  }


  myFile() {
    console.log(this.form.data.id + 'after super.submit-----');
    this.onSubmitProfile(this.fileToUpload, this.userForm).subscribe((data: any) => {

      console.log(this.fileToUpload);
    }, (error: any) => {
      console.log(error);
    });

  }

  onSubmitProfile(fileToUpload: File, userform: FormGroup) {
    const formData = new FormData();
    let phone = null;
    formData.append('file', fileToUpload);
    console.log(this.form.data.id + 'this id number ======');
    return this.httpClient.post("http://localhost:8080/User/profilePic/" + this.form.data.id, formData);
  }

}
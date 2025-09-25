import { Component } from '@angular/core';
import { BaseListCtl } from '../base_list.component';
import { ServiceLocatorService } from '../service-locator.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-faculty-list',
  templateUrl: './faculty-list.component.html',
})
export class FacultyListComponent extends BaseListCtl {

  constructor(locator: ServiceLocatorService, route: ActivatedRoute){
    super(locator.endpoints.FACULTY, locator, route);
  }
}

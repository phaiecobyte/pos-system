import { Component, inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-tenant-form',
  imports: [],
  template: ` <p>tenant-form works!</p> `,
  styles: ``,
})
export class TenantFormComponent {
  private fb = inject(FormBuilder);
  
}

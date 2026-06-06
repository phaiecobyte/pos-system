import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-tenant-form',
  imports: [],
  template: ` <p>tenant-form works!</p> `,
  changeDetection: ChangeDetectionStrategy.Eager,
  styles: ``,
})
export class TenantFormComponent {
  private fb = inject(FormBuilder);
}

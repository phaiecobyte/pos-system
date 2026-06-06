import { Component, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-topbar',
  imports: [],
  template: `
    <header class="h-16 border-b bg-white flex items-center justify-between px-6">
      <h2 class="font-semibold">Super Admin</h2>

      <div>PHAIECOBYTE</div>
    </header>
  `,
  changeDetection: ChangeDetectionStrategy.Eager,
  styles: ``,
})
export class TopbarComponent {}

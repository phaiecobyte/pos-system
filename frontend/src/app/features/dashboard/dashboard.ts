import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  imports: [],
  template: `
    <div>
      <h1 class="text-3xl font-bold">Dashboard</h1>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.Eager,
  styles: ``,
})
export class DashboardComponent implements OnInit {
  ngOnInit() {
    console.log('DashboardComponent initialized');
  }
}

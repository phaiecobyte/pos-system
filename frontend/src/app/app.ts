import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  template: `
    <!-- ទីតាំងនេះហើយដែល Angular នឹងយកផ្ទាំង Login ឬផ្ទាំងផ្សេងៗមកបង្ហាញ -->
    <router-outlet></router-outlet>
  `,
  styles: [``]
})
export class App {
  protected readonly title = signal('frontend');
}

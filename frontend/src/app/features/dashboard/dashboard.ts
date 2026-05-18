import { Component } from '@angular/core';
import { Router } from '@angular/router';

// Angular Material Imports
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule
  ],
  template: `
    <div class="dashboard-container">
      <mat-card elevation="4">
        <mat-card-header>
          <mat-icon mat-card-avatar color="primary">dashboard</mat-icon>
          <mat-card-title>ផ្ទាំងគ្រប់គ្រង (Dashboard)</mat-card-title>
          <mat-card-subtitle>ទិដ្ឋភាពទូទៅនៃប្រព័ន្ធ PhaiEcoByte POS</mat-card-subtitle>
        </mat-card-header>

        <mat-card-content>
          <p>សូមស្វាគមន៍! ការចូលប្រើប្រាស់ (Login) របស់អ្នកទទួលបានជោគជ័យ។</p>
          <!-- ថ្ងៃក្រោយយើងនឹងដាក់តារាង ឬ ក្រាហ្វិក (Charts) នៅទីនេះ -->
        </mat-card-content>

        <mat-card-actions align="end">
          <button mat-stroked-button color="warn" (click)="onLogout()">
            <mat-icon>logout</mat-icon> ចាកចេញ (Logout)
          </button>
        </mat-card-actions>
      </mat-card>
    </div>
  `,
  styles: [`
    .dashboard-container {
      padding: 24px;
      max-width: 1200px;
      margin: 0 auto;
    }
    p {
      margin-top: 16px;
      font-size: 16px;
      color: #333;
    }
  `]
})
export class DashboardComponent {

  constructor(private router: Router) {}

  onLogout() {
    // លុប Token ចោលពី localStorage
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');

    // បញ្ជូនអ្នកប្រើប្រាស់ទៅកាន់ផ្ទាំង Login វិញ
    this.router.navigate(['/login']);
  }
}

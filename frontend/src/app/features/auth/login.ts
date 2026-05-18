import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

// Angular Material Imports
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

// នាំចូល (Import) Service និង Model ដែលបាន Generate ពី Spring Framework
// បញ្ជាក់៖ សូមប្តូរទីតាំង Path នេះទៅតាមឈ្មោះ Service ពិតប្រាកដដែល CLI បាន Generate
import { AuthControllerService } from '../../core/api-client';
import { AuthRequest } from '../../core/api-client';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  template: `
    <div class="login-container">
      <mat-card class="login-card" elevation="4">
        <mat-card-header>
          <mat-card-title>PhaiEcoByte POS</mat-card-title>
          <mat-card-subtitle>សូមស្វាគមន៍! សូមចូលគណនីរបស់អ្នក</mat-card-subtitle>
        </mat-card-header>

        <mat-card-content>
          <!-- បង្ហាញសារកំហុសពេល Login មិនជោគជ័យ -->
          @if (errorMessage) {
            <div class="error-banner">
              <mat-icon>error_outline</mat-icon>
              <span>{{ errorMessage }}</span>
            </div>
          }

          <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" class="login-form">

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>ឈ្មោះគណនី (Username)</mat-label>
              <input matInput formControlName="username" placeholder="បញ្ចូលឈ្មោះគណនី" autocomplete="username">
              <mat-icon matPrefix>person</mat-icon>

              @if (loginForm.get('username')?.hasError('required') && loginForm.get('username')?.touched) {
                <mat-error>ឈ្មោះគណនី គឺចាំបាច់</mat-error>
              }
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>ពាក្យសម្ងាត់ (Password)</mat-label>
              <input matInput [type]="hidePassword ? 'password' : 'text'" formControlName="password" autocomplete="current-password">
              <mat-icon matPrefix>lock</mat-icon>
              <button mat-icon-button matSuffix (click)="hidePassword = !hidePassword" type="button">
                <mat-icon>{{ hidePassword ? 'visibility_off' : 'visibility' }}</mat-icon>
              </button>

              @if (loginForm.get('password')?.hasError('required') && loginForm.get('password')?.touched) {
                <mat-error>ពាក្យសម្ងាត់ គឺចាំបាច់</mat-error>
              }
            </mat-form-field>

            <!-- ប៊ូតុង Login នឹងបង្ហាញ Spinner ពេលកំពុងរង់ចាំ API -->
            <button mat-flat-button color="primary" type="submit" class="full-width login-button" [disabled]="loginForm.invalid || isLoading">
              @if (isLoading) {
                <mat-spinner diameter="24" color="accent"></mat-spinner>
              } @else {
                ចូលប្រើប្រាស់ (Login)
              }
            </button>
          </form>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .login-container { display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f5f5f5; }
    .login-card { width: 100%; max-width: 400px; padding: 24px; border-radius: 12px; }
    mat-card-header { display: flex; flex-direction: column; align-items: center; margin-bottom: 24px; }
    mat-card-title { font-size: 24px; font-weight: bold; color: #3f51b5; margin-bottom: 8px; }
    .login-form { display: flex; flex-direction: column; gap: 16px; }
    .full-width { width: 100%; }
    .login-button { padding: 24px 0; font-size: 16px; font-weight: 500; border-radius: 8px; display: flex; justify-content: center; align-items: center; }
    .error-banner { display: flex; align-items: center; gap: 8px; background-color: #fdecea; color: #f44336; padding: 12px; border-radius: 8px; margin-bottom: 16px; font-size: 14px; }
  `]
})
export class LoginComponent {
  loginForm: FormGroup;
  hidePassword = true;
  isLoading = false;      // សម្រាប់បិទប៊ូតុង និងបង្ហាញ Spinner
  errorMessage = '';      // សម្រាប់ផ្ទុកសារកំហុសពី Backend

  constructor(
    private fb: FormBuilder,
    private authService: AuthControllerService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      const request: AuthRequest = this.loginForm.value;

      this.authService.login(request).subscribe({
        next: (response) => {
          if (response.accessToken && response.refreshToken) {
            localStorage.setItem('access_token', response.accessToken);
            localStorage.setItem('refresh_token', response.refreshToken)
            this.router.navigate(['/dashboard']);
          }
        },
        error: (err) => {
          this.isLoading = false;
          // បង្ហាញសារកំហុសពេល Username/Password ខុស ឬ Server ដាច់
          this.errorMessage = err.error.message;
          console.error('Login Error:', err);
        },
        complete: () => {
          this.isLoading = false;
        }
      });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }
}

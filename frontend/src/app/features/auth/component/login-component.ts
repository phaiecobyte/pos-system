// import { Component, inject, signal } from '@angular/core';
// import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
// import { Router } from '@angular/router';


// import { AuthService } from '../../../core/api/auth-service';

// @Component({
//   selector: 'app-login',
//   standalone: true,
//   imports: [
//     ReactiveFormsModule
//   ],
//   template: ``,
//   styles: [`
//     :host {
//       display: block;
//     }
//   `]
// })
// export class LoginComponent {
//   private fb = inject(NonNullableFormBuilder);
//   private authService = inject(AuthService);
//   private router = inject(Router);

//   // ប្រកាស State ដោយប្រើ Signals ទាំងអស់
//   isLoading = signal(false);
//   errorMessage = signal('');
//   hidePassword = signal(true);

//   // Form ដែលមានប្រភេទ (Typed Form) ច្បាស់លាស់
//   loginForm = this.fb.group({
//     username: ['', Validators.required],
//     password: ['', Validators.required]
//   });

//   // មុខងារសម្រាប់បិទ/បើកមើលលេខសម្ងាត់
//   togglePassword() {
//     this.hidePassword.update(value => !value);
//   }

//   onSubmit() {
//     if (this.loginForm.invalid) return;

//     this.isLoading.set(true);
//     this.errorMessage.set('');

//     this.authService.login(this.loginForm.getRawValue()).subscribe({
//       next: (res) => {
//         // ១. បិទរង្វង់លោត (Spinner) ពេលជោគជ័យ
//         this.isLoading.set(false);

//         // ២. នេះជាចំណុចសំខាន់ដែលបាត់!
//         // បញ្ជូនអ្នកប្រើប្រាស់ទៅកាន់ទំព័រ Dashboard វិញ (ឧទាហរណ៍៖ ទំព័រ super-admin)
//         this.router.navigate(['/super-admin/dashboard']);
//       },
//       error: (err) => {
//         // ៣. បើមានកំហុស (ឧ. ខុស Password) ត្រូវបង្ហាញសារកំហុស
//         this.isLoading.set(false);
//         this.errorMessage.set('ឈ្មោះអ្នកប្រើប្រាស់ ឬពាក្យសម្ងាត់មិនត្រឹមត្រូវទេ!');
//         console.error('Login Failed:', err);
//       }
//     });
//   }
// }

// import { computed, inject, Injectable, signal } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { environment } from '../../../env/env.dev';
// import { LoginReq, LoginRes } from '../../shared/models/shared-model';
// import { tap } from 'rxjs';

// @Injectable({
//   providedIn: 'root',
// })
// export class AuthService {
//   private http = inject(HttpClient);
//   private baseUrl = `${environment.baseUrl}/api/v1/auth`;

//   // ប្រើប្រាស់ឈ្មោះ Key 'access_token' តែមួយគត់
//   private accessToken = signal<string | null>(localStorage.getItem('access_token'));

//   // ត្រូវមាន () នៅពីក្រោយ accessToken ដើម្បីទាញយកតម្លៃពី Signal
//   isAuthenticated = computed(() => this.accessToken() !== null);

//   getToken(): string | null {
//     return this.accessToken();
//   }

//   login(req: LoginReq) {
//     return this.http.post<LoginRes>(`${this.baseUrl}/login`, req).pipe(
//       tap(res => {
//         // រក្សាទុកតែ Access Token ប៉ុណ្ណោះ (ចំណែក Refresh Token គឺវាចូលក្នុង HttpOnly Cookie ដោយស្វ័យប្រវត្តិ)
//         localStorage.setItem('access_token', res.accessToken);
//         this.accessToken.set(res.accessToken);
//       })
//     );
//   }

//   // ==========================================
//   // មុខងារថ្មី៖ សម្រាប់ហៅយក Token ថ្មីដោយស្វ័យប្រវត្តិ (Silent Refresh)
//   // ==========================================
//   refreshToken() {
//     // ហៅទៅកាន់ API ដែលអ្នកបានកំណត់ក្នុង Spring Boot គឺ /refresh-token
//     // យើងមិនបាច់ភ្ជាប់ទិន្នន័យទៅទេ ព្រោះ Browser នឹងភ្ជាប់ HttpOnly Cookie ទៅដោយស្វ័យប្រវត្តិដោយសារ Credentials Interceptor
//     return this.http.post<LoginRes>(`${this.baseUrl}/refresh-token`, {}).pipe(
//       tap(res => {
//         localStorage.setItem('access_token', res.accessToken);
//         this.accessToken.set(res.accessToken);
//       })
//     );
//   }

//   logout() {
//     // ១. លុប Access Token ពីក្នុង Browser
//     localStorage.removeItem('access_token');
//     this.accessToken.set(null);

//     // ២. ហៅទៅ API /logout ដើម្បីឲ្យ Backend លុប HttpOnly Cookie ចោល (ការពារសុវត្ថិភាព)
//     this.http.post(`${this.baseUrl}/logout`, {}).subscribe({
//       next: () => console.log('Logged out from backend successfully'),
//       error: (err) => console.error('Logout API failed', err)
//     });
//   }
// }

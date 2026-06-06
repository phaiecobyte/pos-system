// import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
// import { inject } from '@angular/core';
// import { throwError, catchError, switchMap } from 'rxjs';
// import { AuthService } from '../api/auth-service';

// export const authInterceptor: HttpInterceptorFn = (req, next) => {
//   const authService = inject(AuthService);
//   const token = authService.getToken();

//   // ១. ភ្ជាប់ Access Token ទៅរាល់ Request (លើកលែងតែ API របស់ auth)
//   let authReq = req;
//   if (token && !req.url.includes('/auth/')) {
//     authReq = req.clone({
//       setHeaders: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//   }

//   // ២. បញ្ជូន Request ទៅ Backend និងចាំចាប់កំហុស
//   return next(authReq).pipe(
//     catchError((error: HttpErrorResponse) => {

//       // បើជួបកំហុស 401 (Unauthorized) ហើយវាមិនមែនជាការ Login
//       if (error.status === 401 && !req.url.includes('/auth/login')) {

//         // ៣. លួចហៅទៅយក Access Token ថ្មី
//         // (Browser នឹងភ្ជាប់ Refresh Cookie ទៅដោយស្វ័យប្រវត្តិ ដោយសារ Credentials Interceptor របស់យើងខាងលើ)
//         return authService.refreshToken().pipe(
//           switchMap((response) => {
//             // ពេលបាន Token ថ្មីហើយ យើងធ្វើ Request ដែលបរាជ័យអម្បាញ់មិញនោះម្តងទៀត!
//             const retriedReq = req.clone({
//               setHeaders: {
//                 Authorization: `Bearer ${response.accessToken}`
//               }
//             });
//             return next(retriedReq);
//           }),
//           catchError((refreshError) => {
//             // បើ Refresh Token ខ្លួនឯងហ្នឹងក៏ផុតកំណត់ទៀត មានន័យថាត្រូវបង្ខំឲ្យ Login វិញមែនទែនហើយ
//             authService.logout();
//             return throwError(() => refreshError);
//           })
//         );
//       }

//       // បើជាកំហុសផ្សេង (ឧ. 500 Server Error) បោះវាត្រឡប់ទៅ Component វិញធម្មតា
//       return throwError(() => error);
//     })
//   );
// };

import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { env } from '../../../env/env.dev';
import { ApiResponse, PageResponse, Tenant, TenantForm } from '../models/model';

@Injectable({
  providedIn: 'root',
})
export class TenantApiService {

  private readonly http = inject(HttpClient)

  private readonly apiUrl = `${env.apiUrl}/core/api/v1/tenants`

  list(page: number,size: number,sort: string = 'createdAt,desc')
  {
    return this.http.get<ApiResponse<PageResponse<Tenant>>>
    (`${this.apiUrl}/list`,
      {
        params: {
          page,
          size,
          sort
        }
      }
    );
  }

  create(request: TenantForm) {
    return this.http.post(`${this.apiUrl}/create`,request);
  }


}

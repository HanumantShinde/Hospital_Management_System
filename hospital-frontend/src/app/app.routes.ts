import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () => import('./components/login/login.component')
      .then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./components/register/register.component')
      .then(m => m.RegisterComponent)
  },
  {
    path: 'admin',
    loadComponent: () => import('./components/admin/admin.component')
      .then(m => m.AdminComponent),
    canActivate: [authGuard],
    data: { role: 'ADMIN' }
  },
  {
    path: 'doctor',
    loadComponent: () => import('./components/doctor/doctor.component')
      .then(m => m.DoctorComponent),
    canActivate: [authGuard],
    data: { role: 'DOCTOR' }
  },
  {
    path: 'patient',
    loadComponent: () => import('./components/patient/patient.component')
      .then(m => m.PatientComponent),
    canActivate: [authGuard],
    data: { role: 'PATIENT' }
  },
  {
    path: 'unauthorized',
    loadComponent: () => import('./components/unauthorized/unauthorized.component')
      .then(m => m.UnauthorizedComponent)
  }
];

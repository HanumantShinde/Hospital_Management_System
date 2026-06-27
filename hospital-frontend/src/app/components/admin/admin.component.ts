import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { Doctor } from '../../models/doctor';
import { Patient } from '../../models/patient';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {

  doctors: Doctor[] = [];
  patients: Patient[] = [];
  newDoctor = { name: '', email: '', password: '' };
  newPatient = { name: '', email: '', password: '' };
  activeTab: string = 'doctors';
  successMessage: string = '';
  errorMessage: string = '';

  private baseUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loadDoctors();
    this.loadPatients();
  }

  loadDoctors(): void {
    this.http.get<Doctor[]>(`${this.baseUrl}/doctors`).subscribe({
      next: (data) => this.doctors = data,
      error: (err) => console.error('Error loading doctors', err)
    });
  }

  loadPatients(): void {
    this.http.get<Patient[]>(`${this.baseUrl}/patients`).subscribe({
      next: (data) => this.patients = data,
      error: (err) => console.error('Error loading patients', err)
    });
  }

  addDoctor(): void {
    if (!this.newDoctor.name || !this.newDoctor.email || !this.newDoctor.password) {
      this.showError('Please fill all doctor fields');
      return;
    }
    this.http.post(`${this.baseUrl}/doctors`, this.newDoctor).subscribe({
      next: () => {
        this.loadDoctors();
        this.newDoctor = { name: '', email: '', password: '' };
        this.showSuccess('Doctor added successfully');
      },
      error: (err) => this.showError('Failed to add doctor')
    });
  }

  addPatient(): void {
    if (!this.newPatient.name || !this.newPatient.email || !this.newPatient.password) {
      this.showError('Please fill all patient fields');
      return;
    }
    this.http.post(`${this.baseUrl}/patients`, this.newPatient).subscribe({
      next: () => {
        this.loadPatients();
        this.newPatient = { name: '', email: '', password: '' };
        this.showSuccess('Patient added successfully');
      },
      error: (err) => this.showError('Failed to add patient')
    });
  }

  deleteDoctor(id: number): void {
    if (!confirm('Are you sure you want to delete this doctor?')) return;
    this.http.delete(`${this.baseUrl}/doctors/${id}`, { responseType: 'text' }).subscribe({
      next: () => {
        this.doctors = this.doctors.filter(d => d.id !== id);
        this.showSuccess('Doctor deleted successfully');
      },
      error: (err) => this.showError('Failed to delete doctor')
    });
  }

  deletePatient(id: number): void {
    if (!confirm('Are you sure you want to delete this patient?')) return;
    this.http.delete(`${this.baseUrl}/patients/${id}`, { responseType: 'text' }).subscribe({
      next: () => {
        this.patients = this.patients.filter(p => p.id !== id);
        this.showSuccess('Patient deleted successfully');
      },
      error: (err) => this.showError('Failed to delete patient')
    });
  }

  showSuccess(message: string): void {
    this.successMessage = message;
    this.errorMessage = '';
    setTimeout(() => this.successMessage = '', 3000);
  }

  showError(message: string): void {
    this.errorMessage = message;
    this.successMessage = '';
    setTimeout(() => this.errorMessage = '', 3000);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

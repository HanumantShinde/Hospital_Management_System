import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { Doctor } from '../../models/doctor';
import { Appointment } from '../../models/appointment';
import { Patient } from '../../models/patient';

@Component({
  selector: 'app-patient',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './patient.component.html',
  styleUrl: './patient.component.css'
})
export class PatientComponent implements OnInit {

  doctors: Doctor[] = [];
  appointments: Appointment[] = [];
  selectedDoctorId: number | null = null;
  appointmentDate: string = '';
  notes: string = '';
  activeTab: string = 'book';
  successMessage: string = '';
  errorMessage: string = '';
  editingProfile: boolean = false;

  profile: Patient = {
    id: 0, name: '', email: '',
    age: 0, bloodGroup: '', phone: '', address: ''
  };

  private baseUrl = 'http://localhost:8080/api/patient';

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loadDoctors();
    this.loadAppointments();
    this.loadProfile();
  }

  loadProfile(): void {
    this.http.get<Patient>(`${this.baseUrl}/profile`).subscribe({
      next: (data) => this.profile = data,
      error: (err) => console.error('Error loading profile', err)
    });
  }

  loadDoctors(): void {
    this.http.get<Doctor[]>(`${this.baseUrl}/doctors`).subscribe({
      next: (data) => this.doctors = data,
      error: (err) => console.error('Error loading doctors', err)
    });
  }

  loadAppointments(): void {
    this.http.get<Appointment[]>(`${this.baseUrl}/appointments`).subscribe({
      next: (data) => this.appointments = data,
      error: (err) => console.error('Error loading appointments', err)
    });
  }

  bookAppointment(): void {
    if (!this.selectedDoctorId || !this.appointmentDate) {
      this.showError('Please select a doctor and appointment date');
      return;
    }

    const appointment = {
      doctor: { id: this.selectedDoctorId },
      appointmentDate: this.appointmentDate,
      notes: this.notes
    };

    this.http.post(`${this.baseUrl}/appointments`, appointment).subscribe({
      next: () => {
        this.loadAppointments();
        this.selectedDoctorId = null;
        this.appointmentDate = '';
        this.notes = '';
        this.showSuccess('Appointment booked successfully!');
        this.activeTab = 'appointments';
      },
      error: (err) => this.showError('Failed to book appointment')
    });
  }

  updateProfile(): void {
    this.http.put<Patient>(`${this.baseUrl}/profile`, {
      age: this.profile.age,
      bloodGroup: this.profile.bloodGroup,
      phone: this.profile.phone,
      address: this.profile.address
    }).subscribe({
      next: (data) => {
        this.profile = data;
        this.editingProfile = false;
        this.showSuccess('Profile updated successfully!');
      },
      error: (err) => this.showError('Failed to update profile')
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

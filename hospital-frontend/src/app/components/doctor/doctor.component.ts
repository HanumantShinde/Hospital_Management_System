import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { Appointment } from '../../models/appointment';
import { Doctor } from '../../models/doctor';

@Component({
  selector: 'app-doctor',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './doctor.component.html',
  styleUrl: './doctor.component.css'
})
export class DoctorComponent implements OnInit {

  appointments: Appointment[] = [];
  selectedAppointment: Appointment | null = null;
  updatedNotes: string = '';
  updatedStatus: string = '';

  profile: Doctor = {
    id: 0, name: '', email: '',
    specialization: '', experience: 0, phone: ''
  };

  editingProfile: boolean = false;
  profileSuccess: string = '';

  activeTab: string = 'appointments';

  private baseUrl = 'http://localhost:8080/api/doctor';

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loadAppointments();
    this.loadProfile();
  }

  loadProfile(): void {
    this.http.get<Doctor>(`${this.baseUrl}/profile`).subscribe({
      next: (data) => this.profile = data,
      error: (err) => console.error('Error loading profile', err)
    });
  }

  loadAppointments(): void {
    this.http.get<Appointment[]>(`${this.baseUrl}/appointments`).subscribe({
      next: (data) => this.appointments = data,
      error: (err) => console.error('Error loading appointments', err)
    });
  }

  selectAppointment(appointment: Appointment): void {
    this.selectedAppointment = appointment;
    this.updatedNotes = appointment.notes || '';
    this.updatedStatus = appointment.status;
  }

  updateAppointment(): void {
    if (!this.selectedAppointment) return;

    const updated = {
      status: this.updatedStatus,
      notes: this.updatedNotes
    };

    this.http.put(`${this.baseUrl}/appointments/${this.selectedAppointment.id}`, updated).subscribe({
      next: () => {
        this.appointments = this.appointments.map(a =>
          a.id === this.selectedAppointment!.id
            ? { ...a, status: this.updatedStatus, notes: this.updatedNotes }
            : a
        );
        this.selectedAppointment = null;
      },
      error: (err) => console.error('Error updating appointment', err)
    });
  }

  updateProfile(): void {
    this.http.put<Doctor>(`${this.baseUrl}/profile`, {
      specialization: this.profile.specialization,
      experience: this.profile.experience,
      phone: this.profile.phone
    }).subscribe({
      next: (data) => {
        this.profile = data;
        this.editingProfile = false;
        this.profileSuccess = 'Profile updated successfully!';
        setTimeout(() => this.profileSuccess = '', 3000);
      },
      error: (err) => console.error('Error updating profile', err)
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

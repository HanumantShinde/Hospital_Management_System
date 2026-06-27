import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    if (!this.email || !this.password) {
      this.errorMessage = 'Please enter email and password';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.authService.saveToken(response.token);
        this.authService.saveRole(response.role);

        if (response.role === 'ADMIN') this.router.navigate(['/admin']);
        else if (response.role === 'DOCTOR') this.router.navigate(['/doctor']);
        else if (response.role === 'PATIENT') this.router.navigate(['/patient']);
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = 'Invalid email or password. Please try again.';
      }
    });
  }
}

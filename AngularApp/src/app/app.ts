import { HttpClient } from '@angular/common/http';
import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('AngularApp');

  constructor(private http: HttpClient) {}

  public loadProducts() {
    console.log('loading products');
    this.http.get<any>('localhost:8080/api/products').subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (error) => {
        console.log('Error loading products', error);
      },
    });
  }
}

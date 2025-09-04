# Hospital Management System (HMS) Frontend

A modern, responsive Angular-based frontend application for managing hospital operations including appointments, patients, doctors, medical records, billing, and more.

## 🚀 Features

- **Dashboard**: Overview with key metrics and recent activities
- **Appointment Management**: Schedule, view, and manage patient appointments
- **Patient Management**: Complete patient registration and information management
- **Doctor Management**: Doctor profiles, availability, and information
- **Medical Records**: Patient medical history and treatment records
- **Billing System**: Generate and manage patient bills
- **Medicine Management**: Track medicine inventory
- **Invoice Generation**: Create and download invoices
- **Responsive Design**: Works seamlessly on desktop, tablet, and mobile devices
- **Modern UI**: Built with Angular Material and Bootstrap for a professional look

## 🛠️ Technology Stack

- **Frontend Framework**: Angular 17
- **UI Components**: Angular Material
- **Styling**: Bootstrap 5 + Custom CSS
- **Charts**: Chart.js with ng2-charts
- **HTTP Client**: Angular HttpClient
- **Forms**: Reactive Forms with validation
- **Routing**: Angular Router

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Node.js** (version 18 or higher)
- **npm** (comes with Node.js)
- **Angular CLI** (version 17 or higher)

## 🚀 Installation & Setup

### 1. Install Dependencies

```bash
npm install
```

### 2. Start Development Server

```bash
ng serve
```

The application will be available at `http://localhost:4200/`

### 3. Build for Production

```bash
ng build --configuration production
```

## 📁 Project Structure

```
src/
├── app/
│   ├── components/
│   │   ├── auth/                 # Authentication components
│   │   │   ├── login/
│   │   │   └── register/
│   │   ├── layout/               # Layout components
│   │   │   ├── header/
│   │   │   └── sidebar/
│   │   ├── dashboard/            # Dashboard component
│   │   ├── appointment/          # Appointment management
│   │   ├── patient/              # Patient management
│   │   ├── doctor/               # Doctor management
│   │   ├── medical-record/       # Medical records
│   │   ├── billing/              # Billing management
│   │   ├── medicine/             # Medicine inventory
│   │   └── invoice/              # Invoice generation
│   ├── models/                   # TypeScript interfaces
│   ├── services/                 # API services
│   ├── app.component.*           # Main app component
│   ├── app.module.ts            # Main module
│   └── app-routing.module.ts    # Routing configuration
├── assets/                       # Static assets
└── styles.css                   # Global styles
```

## 🔧 Configuration

### Backend API Configuration

The application is configured to connect to a Spring Boot backend running on `http://localhost:8080`. Update the API URLs in the services if your backend is running on a different port or host.

### Environment Variables

Create an `environment.ts` file in `src/environments/` if you need to configure different environments:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api/v1'
};
```

## 📱 Key Components

### Dashboard
- Summary cards showing key metrics
- Recent appointments table
- Recent doctors list
- Out-of-stock medicines alert
- Patient activity chart (placeholder)

### Appointment Management
- Add new appointments with patient and doctor selection
- View all appointments in a table format
- Search and filter appointments
- Edit and delete appointments
- Status management (Pending, Accepted, Rejected)

### Patient Management
- Complete patient registration form
- Patient information management
- Search and filter patients
- Password management with visibility toggle

### Doctor Management
- Doctor registration and profile management
- Availability and consultancy charge settings
- Search and filter doctors
- Status management

### Medical Records
- Create medical records for patients
- Link records to doctors and appointments
- View patient medical history
- Diagnosis and treatment tracking

### Billing System
- Generate bills for patients
- Payment status tracking
- Bill amount calculation
- Payment processing

## 🎨 UI/UX Features

- **Responsive Design**: Mobile-first approach
- **Modern Interface**: Clean and professional design
- **Interactive Elements**: Hover effects and animations
- **Status Indicators**: Color-coded badges for different statuses
- **Form Validation**: Real-time validation with error messages
- **Loading States**: Loading indicators for better UX
- **Toast Notifications**: Success/error message display

## 🔒 Security Features

- Form validation on both client and server side
- Password visibility toggle for secure input
- Input sanitization
- CORS configuration for API calls

## 📊 Data Management

The application uses Angular services to manage data:

- **AppointmentService**: Handles appointment CRUD operations
- **PatientService**: Manages patient data
- **DoctorService**: Handles doctor information
- **MedicalRecordService**: Manages medical records
- **BillingService**: Handles billing operations

## 🚀 Deployment

### Build for Production

```bash
ng build --configuration production
```

### Deploy to Static Hosting

The built files will be in the `dist/` folder, which can be deployed to any static hosting service like:

- Netlify
- Vercel
- GitHub Pages
- AWS S3
- Firebase Hosting

## 🔧 Development

### Code Style

- Follow Angular style guide
- Use TypeScript strict mode
- Implement proper error handling
- Add comments for complex logic

### Testing

```bash
# Run unit tests
ng test

# Run end-to-end tests
ng e2e
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:

1. Check the documentation
2. Review existing issues
3. Create a new issue with detailed information

## 🔄 Updates

Keep your dependencies updated:

```bash
npm update
ng update @angular/core @angular/cli
```

---

**Note**: This frontend application is designed to work with a Spring Boot backend. Make sure your backend is running and properly configured before testing the frontend features.

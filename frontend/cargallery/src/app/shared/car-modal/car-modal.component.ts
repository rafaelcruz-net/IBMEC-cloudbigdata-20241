import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MarcaService } from '../../services/marca.service';

@Component({
  selector: 'app-car-modal',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './car-modal.component.html',
  styleUrl: './car-modal.component.css'
})
export class CarModalComponent {
  nome = new FormControl('', [Validators.required]);
  descricao = new FormControl('', [Validators.required]);

  constructor(private marcarService:MarcaService) {}

  public criar() {
    let inputFile = document.getElementById("fileImg");

    var reader = new FileReader();
    reader.readAsDataURL((inputFile as any).files[0]);
    reader.onload = (result) => {
      let base64Img = reader.result?.toString().replace(/^data:image\/?[A-z]*;base64,/, "");
      let request = {
        nome: this.nome.value,
        descricao: this.descricao.value,
        imagemBase64: base64Img
      }

      this.marcarService.criarCarro(request).subscribe(response => {
        console.log(response);
      });


  

    };

  }
}

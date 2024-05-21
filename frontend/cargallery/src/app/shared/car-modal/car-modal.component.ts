import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MarcaService } from '../../services/marca.service';
import { Marca } from '../../model/marca';

@Component({
  selector: 'app-car-modal',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './car-modal.component.html',
  styleUrl: './car-modal.component.css'
})
export class CarModalComponent implements OnInit {
  nome = new FormControl('', [Validators.required]);
  descricao = new FormControl('', [Validators.required]);
  marca = new FormControl('', [Validators.required]);
  itensMarca = new Array<Marca>();
  @Output() onCreated = new EventEmitter();

  constructor(private marcarService:MarcaService) {}

  ngOnInit(): void {
    this.marcarService.obterMarcas().subscribe(response => {
      this.itensMarca = response;
    });
  }

  public criar() {
    let inputFile = document.getElementById("fileImg");

    var reader = new FileReader();
    reader.readAsDataURL((inputFile as any).files[0]);
    reader.onload = (result) => {
      let idMarca = this.marca.value;
      let base64Img = reader.result?.toString().replace(/^data:image\/?[A-z]*;base64,/, "");
      let request = {
        nome: this.nome.value,
        descricao: this.descricao.value,
        imagemBase64: base64Img
      }

      this.marcarService.criarCarro(request, idMarca as String).subscribe(response => {
        this.onCreated.emit();
        document.getElementById("btnClose")?.click();
      });
    };

  }
}

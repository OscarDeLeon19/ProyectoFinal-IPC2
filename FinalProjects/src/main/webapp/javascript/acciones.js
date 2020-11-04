
function ingresoGerente(){
    document.getElementById("div1").removeAttribute("hidden");
    document.getElementById("div2").setAttribute("hidden", "hidden");
}

function ingresoCajero(){
    document.getElementById("div2").removeAttribute("hidden");
}
function salirGerente(){
    document.getElementById("div1").setAttribute("hidden", "hidden");
} 

function salirCajero(){
    document.getElementById("div2").setAttribute("hidden", "hidden");
} 





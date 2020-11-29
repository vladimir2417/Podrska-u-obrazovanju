function testEmail() {
    var emailNas = document.getElementById("email");
    var regExp = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/g;
    var rezultat = emailNas.value.match(regExp);

    if (rezultat != null)
        return true;
      else {
        emailNas.setCustomValidity("Email nije u dobrom formatu.");
        emailNas.focus();
        return false;
    }
}

function zabranaUnosaSpecKarIme()
{
    var unos=/[^a-zA-Z]/;
    var ime=document.getElementById("ime");

    if(ime.value!=="")
    {
        if(unos.test(ime.value))
        {
            ime.setCustomValidity("Zabranjen je unos specijalnih karaktera u imenu.");
            ime.focus();
            return false;
        }
    }
}

function zabranaUnosaSpecKarPrezime()
{
    var unos=/[^a-zA-Z]/;
    var prezime=document.getElementById("prezime");

    if(prezime.value!=="")
    {
        if(unos.test(prezime.value))
        {
            prezime.setCustomValidity("Zabranjen je unos specijalnih karaktera u prezimenu.");
            prezime.focus();
            return false;
        }
    }
}
window.onscroll = function() {makeSticky()};

var navbar = document.querySelector('.Exit');
// var sticky = navbar.offsetTop - 80; // Adjusts for the initial 80px offset
var sticky = 80

function makeSticky() {
  if ((window.scrollY  )>= sticky) {
    navbar.classList.add("Sticky")
  } else {
    navbar.classList.remove("Sticky");
  }
}

// || window.pageYOffset
function getRandomInt() {
    return Math.floor(Math.random() * 5);
  }
  
  function pickMe(){
    var num = getRandomInt();
    if(num==0){
        window.location.href = "https://www.youtube.com/watch?v=2Z4m4lnjxkY";
    }
    else if(num==1){
        window.location.href = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
    }
    else if(num==2){
        window.location.href = "https://www.youtube.com/watch?v=9bZkp7q19f0"
    }
    else if(num==3){
        window.location.href = "https://www.youtube.com/watch?v=jofNR_WkoCE"
    }
    else if(num==4){
        window.location.href = "https://www.youtube.com/watch?v=WxisiPxgtSk"
    }
  }


  
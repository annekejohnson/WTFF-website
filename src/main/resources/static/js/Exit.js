// window.onscroll = function() {makeSticky()};


let title = ["Slide #1", "Slide #2", "Slide 3"];
let desc = ["Lorem ipsum dolor sit amet consectetur adipisicing elit. Quaerat dignissimos commodi eos totam perferendis possimus dolorem, deleniti vitae harum? Enim.",
"Ipsum dolor sit amet consectetur adipisicing elit. Quaerat dignissimos commodi eos totam perferendis possimus dolorem, deleniti vitae harum? Enim.",
"dolor sit amet consectetur adipisicing elit. Quaerat dignissimos commodi eos totam perferendis possimus dolorem, deleniti vitae harum? Enim."]
let pic = ["img/Carousel/deranged.png", "img/Carousel/kermit.png", "img/Carousel/habitat.jpeg"]
var navbar = document.querySelector('.Exit');
var sticky = 80
let index = 1;

function makeSticky() {
  if ((window.scrollY  )>= sticky) {
    navbar.classList.add("Sticky")
  } else {
    navbar.classList.remove("Sticky");
  }
}

function updateContent() {
  console.log("hi");
  let slideTitle = document.querySelector(".slide-title");
  let slideDesc = document.querySelector(".slide-text");
  let slidePic = document.querySelector(".slide-image img");

  slideTitle.innerText = title[index];
  slideDesc.innerText = desc[index];
  slidePic.src = pic[index];
  console.log(index);
  console.log(pic[index]);
  index = (index + 1) % title.length;
}

setInterval(updateContent, 5000);


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


  // function timeout(){
  //   document.addEventListener("DOMContentLoaded", function() {
  //     let logoutTimer;
  //     const idleTimeout = 60 * 1000; // 1 minute
    
  //     const resetTimer = () => {
  //       clearTimeout(logoutTimer);
  //       logoutTimer = setTimeout(() => {
  //         // User has been idle for 1 minute, log them out
  //         window.location.href = '/logout'; // Redirect to your logout URL
  //       }, idleTimeout);
  //     };
    
  //     // Listen for any of these events to reset the timer
  //     document.addEventListener('mousemove', resetTimer, false);
  //     document.addEventListener('mousedown', resetTimer, false);
  //     document.addEventListener('keypress', resetTimer, false);
  //     document.addEventListener('touchmove', resetTimer, false);
  //     document.addEventListener('onclick', resetTimer, false);
    
  //     resetTimer(); // Initialize the timer
  //   });
  
  // }
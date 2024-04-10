// window.onscroll = function() {makeSticky()};


let title = ["Education/ Refresher on Life Skills", "Group Support", "Teen/ Family Nights"];
let desc = ["Domestic violence and trauma can significantly impact an individual's mental and emotional well-being, resulting in damage to various aspects of their cognitive processing. We will offer classes that teach how trauma effects the brain and body, self discovery lessons to rediscover how one learns, healthy relationships and more!",
"While similar in name, Group Support and Classes with a specific focus differ. In the classroom setting, we learn specific topics relevant to the group, whereas in the latter, women gather to share their experiences and learn coping skills, often concluding with a meditative session. This approach helps reduce the sense of isolation that those fleeing dangerous situations often experience.",
"We are establishing a secure environment tailored for adolescents to recover from family crises. This Safe Space will offer a nurturing setting for interaction, learning, and healing from trauma, while planning community projects that will benefit the family and the community."]
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
# CMPT276_TheBestGroupProject

Wellness Truth and Families Foundation

Project Proposal

ABSTRACT

This project aims to establish a website that will serve educational and supportive purposes. This platform is meant to provide a safe space for women and families who have gone through domestic violence or other traumas, facilitating access to essential resources, courses, and community support. The primary audience for this project includes women from diverse backgrounds within the Fort St. John community, particularly those of lower socio-economic status who have faced domestic violence or significant trauma. This includes immigrants, refugees, and Indigenous populations, who are often underserved and desperately needs support and resources

Do we have a clear understanding of the problem?

How is this problem solved currently (if at all)?

- Grant applications: Most funding and grant applications ask for a website
- Digital accessbility: can be accessed from anywhere and is more accessible.
- Broader scope of participants: can handle more participants than a physical space. Staffs and specialized support can be focused to the ones who actually needs it as they are limited in number.
- Engagement and advertisement: The website can facilitate ongoing engagement through features like a news bulletin, event calendars, and the possibility to subscribe or unsubscribe from courses.
- Centralized resource hub that is not limited to facebook(assuming that is what the foundation currently uses) features
- Automation: Volunteer applications and donations can be automated to an extent which reduces the human workload.

How will this project make life better? Is it educational or just for entertainment?

- Provides a safe space for women and families who had gone through domestic violence
- The website will serve as a platform to facilitate access to these resources, courses, and community support, making it more accessible for women to engage in their healing journey.

Who is the target audience? Who will use your app or play your game?

- The target audience is women from diverse backgrounds, primarily those of lower socio-economic status in the Fort St. John community, who have experienced domestic violence or significant trauma. This includes, but is not limited to, immigrants, refugees, and Indigenous populations who are underserved and in need of support.

The current problem revolves around the need for a more accessible platform to support women and families affected by domestic violence, particularly in the Fort St. John community. This problem is addressed through various ways, including grant and funding applications that often requires a website. Furthermore, a website ensures digital accessibility from anywhere, increasing the reach and inclusivity of the supports provided as well as allowing a broader scope of participants. Unlike physical spaces, a website can manage a larger audience while directing limited resources and specialized support where needed. Furthermore, this website will act as a hub for engagement, information, and resources. There is the possibility of automating processes such as volunteer applications and donations which will reduce the workload on human resources.


USER STOIRES/SENARIOS
User Registration and Login : As a new user , they can register for an account on the Wellness Truth & Families Foundation website , providing basic information .

 Course Enrollment : They can browse through the available trauma recovery courses offered by the foundation . They can have the ability to enroll in courses that align with their needs and schedule , gaining access to structured educational content and interactive learning materials 

Interactive Courses : They can participate in courses conducted by mental health professionals and trauma survivors . These courses can provide practical tools , coping strategies , and therapeutic techniques to aid in my healing process . 

Wellness Activities : They can explore a variety of wellness activities . These activities promote relaxation , self - expression , and emotional healing in a safe and supportive environment .


GROUP MEETING MINUTES
## Feb 5th

Potential Projects:

- Genshin tracker
- Course tracker
- Check courses, suggest future courses, etc.
- Client: Academic advising
- Women’s Resource Society or Wellness Truth and Families Foundation

Next meeting: Feb 7th after class

## Feb 7th

– no meeting; no development

## Feb 23rd 1 PM - 2.30 PM

- Hypothetical situation:
- Ensure not anyone can sign into the classes – compare with ‘existing information that Lisa has’
- If email does not match, cannot create an account. If match, create acc (with password)
- Webdev project is a prototype and not going to be used real time
- **Web-rest API** ~~and plain web-API question on GP proposal?~~
- Google Calendar API – paid, not implementing that
- Paypal for donations
- ***Clarified: 1 web-REST API***
- Emergency Exit button task demoted to a small feature
- Could link to google’s “I’m feeling lucky” button instead of generating a low-data (quick to load) website
- News bulletin using facebook embed feature
- Proposal for an admin curation but can also use tag filtration
- Also Railway deployed code??
- A development style?
- Still want to implement calendar to allow users to keep up to date with upcoming events
- Implemented in both **front page** and **courses page**
- Option for accepting volunteers will be changed from a sign-up page to a link to an email address
- Implementation of navigation tabs on **front page**
- Leads to separate pages of: FAQ, in-depth about us + contact info, courses preview
- The FAQ will have a google forms option
- Still undecided how the **courses page** will look like
- Awaiting for Lisa’s answer tomorrow
- But takes Canvas as an inspiration
- Has a help page for how to navigate through the course website / use features / etc. for the technologically illiterate
- And underneath a new feature in every course, will have a link to the corresponding link from the help page
- Help page also acts as a mini FAQ
- Proposal for implementation of a forum (support group discussion) but looked down on the complexity and moderation involved
- ++ links to external website → more unnecessary sign ins
- Awaiting pictures or poster events to be used in carousel feature
- Decided next meeting is tomorrow at 3 PM.

## Feb 24th 3 PM

- Collaborated over text on what each of us were going to get done

CLIENT MEETING MINUTES

## Feb 15
The Wellness Truth and Families Foundation is a brand new non-profit focused on helping families (focused on the moms, but "mom won't heal if the kids are hurting") in Northern BC, particularly in Fort St John. Ran by Lisa, a socialworker who has previously worked with another Fort St John non-profit that was focused on getting women and families out of the abusive and/or traumatic situations. She'll send a few documents our way detailing more about the organization soon. She wants a website.

## Feb 16
Lisa sent over the documents. As there is no physical location for the non-profit yet, she's focused on teaching classes about how trauma affects the brain, how to recognize unhealthy relationships, things like that. She requested a page for donations (even up North, buying property costs money), a page describing the programs and how they link together, and the ability for people to request certain topics for a class or certain programming. I suggested allowing people to sign up for the classes online, adding a calendar so people can see when the classes take place, and adding a Facebook embed to the front page so she doesn't need to worry about posting important information in a million different spots. She liked all of these ideas.

## Feb 22
Quick meeting to clarify a few things before the proposal is due. She needs a website because most grant applications ask for one. It's also a convenient way to introduce staff/board, and have volunteer packages available to lower the barrier for volunteering. Classes are in person, but she also has zoom options to help reduce barriers.

## March 1 2:30 PM
Distribution of work
Violet: Login feature
Quinnie helps Violet, if Violet is good, then work on 2 web pages
Brooklyn: HTML and CSS (?)
They proposed for the 4 rest of us: work on 2 web pages

Webpages:
Front page (HOME) (Brook)
Has Paypal API and Facebook Embed
Must have hyperlinks to following page:
FAQ (Quinnie)
About Us (Brook)
Contacts 
Sign up feature
Donations (Pluto)
Paypal API
Inspiration: Internet Archive
Resources (Pluto)
Courses page (Arielle)
Calendar API
upcoming courses / Lorem Ipsum
Sign up feature
List of all the possible courses
Volunteer (Arielle)
Consistent UI feature on all page (
Navigation tabs – dropdown feature
(Quinnie & Arielle)
LogIn feature in there / link to log in page
Emergency Exit – part of the sidebar ← float
MOCKUP OF THE FRONT PAGES
CSS afterwards
Making new Render account to make a database (only 1 database per account)

Report – similar to Group Proposal’s user stories, but more specified I think
	More focused discussion on March 6th

## March 6th After Class
Navigation bar to be sticky / follows
No sidebar
Exit button in the navigation bar
Database
Has 2 tables:
Users: username, user_type, password, courses
Courses: coursename, c_date, description, location, LINK
Use bootstrap for css
Discussing github push & pull
Need to ask LIsa:
Courses
An example course (name, desc, etc..)
Are the courses linear?
How frequent are classes?
about me page informations
FAQ questions, 
Home page layout description

## Arielle's notes for the TA's for iteration 1 checking
go for vv for what we got so far
  http://localhost:8080/home.html
go for vv for the login feature
  http://localhost:8080/login

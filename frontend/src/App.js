import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Login from './pages/Login';
import Join from './pages/Join';
import User from './pages/User';
import About from './pages/About';
import LoginContextProvider from './contexts/LoginContextProvider';
import Admin from './pages/Admin';

export default function App() {
  return (
    <BrowserRouter>
      <LoginContextProvider>
        <Routes>
          <Route path="/" element={ <Home /> }></Route>
          <Route path="/login" element={ <Login /> }></Route>
          <Route path="/join" element={ <Join /> }></Route>
          <Route path="/user" element={ <User /> }></Route>
          <Route path="/about" element={ <About /> }></Route>
          <Route path="/admin" element={ <Admin /> }></Route>
        </Routes>
      </LoginContextProvider>
    </BrowserRouter>
  );
}

// export default App;

// import React, { useEffect, useState } from "react";
// import { Scheduler } from "@bitnoi.se/react-scheduler"

// export default function App() {
//     const [isLoading, setIsLoading] = useState(false);
//     const data = [
//   {
//     id: "070ac5b5-8369-4cd2-8ba2-0a209130cc60",
//     label: {
//       icon: "https://picsum.photos/24",
//       title: "Joe Doe",
//       subtitle: "Frontend Developer"
//     },
//     data: [
//       {
//         id: "8b71a8a5-33dd-4fc8-9caa-b4a584ba3762",
//         startDate: new Date("2023-04-13T15:31:24.272Z"),
//         endDate: new Date("2023-08-28T10:28:22.649Z"),
//         occupancy: 3600,
//         title: "Project A",
//         subtitle: "Subtitle A",
//         description: "array indexing Salad West Account",
//         bgColor: "rgb(254,165,177)"
//       },
//       {
//         id: "22fbe237-6344-4c8e-affb-64a1750f33bd",
//         startDate: new Date("2023-10-07T08:16:31.123Z"),
//         endDate: new Date("2023-11-15T21:55:23.582Z"),
//         occupancy: 2852,
//         title: "Project B",
//         subtitle: "Subtitle B",
//         description: "Tuna Home pascal IP drive",
//         bgColor: "rgb(254,165,177)"
//       },
//       {
//         id: "3601c1cd-f4b5-46bc-8564-8c983919e3f5",
//         startDate: new Date("2023-03-30T22:25:14.377Z"),
//         endDate: new Date("2023-09-01T07:20:50.526Z"),
//         occupancy: 1800,
//         title: "Project C",
//         subtitle: "Subtitle C",
//         bgColor: "rgb(254,165,177)"
//       },
//       {
//         id: "b088e4ac-9911-426f-aef3-843d75e714c2",
//         startDate: new Date("2023-10-28T10:08:22.986Z"),
//         endDate: new Date("2023-10-30T12:30:30.150Z"),
//         occupancy: 11111,
//         title: "Project D",
//         subtitle: "Subtitle D",
//         description: "Garden heavy an software Metal",
//         bgColor: "rgb(254,165,177)"
//       }
//     ]
//   }
// ];

//     useEffect(() => {
//       setIsLoading(true);

      
//       setIsLoading(false);
//     }, []);

//     return (
//        <Scheduler
//         // decide when to show loading indicators
//         isLoading={isLoading}
//         // your data
//         data={data}
//         // callback when user click's on one of the grid's tile
//         onItemClick={(clickedItem) => console.log(clickedItem)}
//         // filter function that let's you handling filtering on your end
//         onFilterData={() => {
//           // filter your data
//         }}
//         // callback when user clicks clearing filter button
//         onClearFilterData={() => {
//           // clear all your filters
//         }}
//         config={{
//           /* 
//             change filter button state based on your filters
//             < 0 - filter button invisible,
//             0 - filter button visible, no filter applied, clear filters button invisible,
//             > 0 - filter button visible, filters applied (clear filters button will be visible)
//           */
//           filterButtonState: 0,
//           // decide start zoom variant (0 - weeks, 1 - days)
//           zoom: 0,
//           // select language for scheduler
//           lang: "en",
//           // decide how many resources show per one page
//           maxRecordsPerPage: 20,
//         }}
//       />
//     );
//   }


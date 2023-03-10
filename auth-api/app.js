import express from "express";

import * as db from './scr/config/db/initialData.js';
import userRoutes from "./scr/config/modules/user/routes/UserRoutes.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

db.createInitialData();

app.get('/api/status', (req,res)=>{
  return res.json({
    service: "Auth-api",
    status: "up",
    httpStatus: 200,
  })
});

app.use(express.json());
app.use(userRoutes);




app.listen(PORT, () =>{
  console.info(`Server started successfully at port ${PORT}`) 
});
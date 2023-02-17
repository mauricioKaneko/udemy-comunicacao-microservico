import jwt from "jsonwebtoken";
import {promisify } from "util";
import * as httpStatus from "../constants/HttpStatus.js"
import * as secrets from "../constants/secret.js";
import AuthTokenException from "../auth/AcessTokenException.js"

const bearer = "bearer";
const emptySpace = " ";

export default async (req, res, next)=>{
  try{

    const{ authorization} = req.headers;
    if(!authorization){
      throw new AuthTokenException(httpStatus.UNAUTHORIZED, "TOken de acesso nao foi informado");
    }

    let acessToken = authorization;
    if(acessToken.includes(emptySpace)){
      acessToken = acessToken.split(emptySpace)[1];
    } else{
      acessToken = authorization;
    }

    const decoded = await promisify(jwt.verify)(acessToken, secrets.API_SECRET);

    req.authUser = decoded.authUser;
    return next();
  
  }catch(err){
    const status =  err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR;
    return res.status(status).json({ status, message: err.message});
  }
}

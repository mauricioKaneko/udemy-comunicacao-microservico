import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import userRepository from '../repository/userRepository.js';
import * as httpStatus from "../../../../config/constants/HttpStatus.js";
import UserException from '../Exception/UserException.js';
import * as secrets from "../../../../config/constants/secret.js"



class UserService{
  async findByEmail(req){
    try {
      const { email } = req.params;
      const {authUser}= req;
      this.validateRequestData(email);
      let user = await userRepository.findByEmail(email);
      this.validarUserNotFound(user);
      this.validateAuthenticateUser(user, authUser)
      return {
        status: httpStatus.SUCESS,
        user:{
          id: user.id,
          nome: user.nome,
          email: user.email,
        }
      }

    }catch(err){
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message,
      }
    }

  }

  async getAcessToken(req){
    try{

      const { email, password} = req.body;
      console.log("email",email);
      
      this.validateAcessTokenData(email, password);
      let user = await userRepository.findByEmail(email);
      this.validarUserNotFound(user);
      await this.validatePassword(password, user.password);
      const authUser = {id: user.id, 
                        name: user.name,
                        email: user.email};
      const acessToken = jwt.sign({authUser}, secrets.API_SECRET ,{expiresIn: "1d"});

      return {
        status: httpStatus.SUCESS,
        acessToken
      }
    }catch(err){
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message,
      }
    }

  }
  validateAcessTokenData(email, password){
    if(!email || !password){
      throw new UserException(httpStatus.UNAUTHORIZED,"Email e password precisa ser informado");
    }
  }
  validateRequestData(email){
    if(!email){
      throw new UserException(httpStatus.BAD_REQUEST, "Email não encontrado");
    }
  }

  validarUserNotFound(user){
    if(!user){
      throw new Error(httpStatus.BAD_REQUEST, "Usuario Não Encontrado")
    }
  }

  validateAuthenticateUser(user, authUser){
    if (!authUser || user.id !== authUser.id){
      throw new UserException(httpStatus.FORBIDDEN, "Voce não pode ver os dados deste usuario")
    }
  }

  async validatePassword(password, hashPassword){
    if (!await bcrypt.compare(password, hashPassword)){
      throw new UserException(httpStatus.UNAUTHORIZED, "Senha Incorreta");
    }
  }



}

export default new UserService();
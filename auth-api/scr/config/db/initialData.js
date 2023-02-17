import bcrypt from "bcrypt";
import User from "../modules/user/model/User.js";

export async function createInitialData(){
  try{
    await User.sync({force: true});

    let password = await bcrypt.hash("123456", 10);
    await User.create({
      name: 'User test',
      email: "user.test@teste.com",
      password : password,
    })
    await User.create({
      name: 'User test 2',
      email: "user.test2@teste.com",
      password : password,
    })

  }catch(err){
    console.log(err);
  }
}
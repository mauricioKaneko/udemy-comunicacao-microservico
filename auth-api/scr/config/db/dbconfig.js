import Sequelize  from 'sequelize';

const sequelize = new Sequelize("auth-db", "admin", "123456",{
  host: "localhost",
  dialect: "postgres",
  quoteIdentifiers: "false",
  define: {
    syncOnAssociation: true,
    timestamps: false,
    underscored: true,
    underscoredAll: true,
    freezeTableName: true
  }
} );

sequelize.authenticate().then(() => {
  console.info("Conexão foi estabelecida")
}).catch((err)=>{
  console.error("Sem conexão com o banco de dados ");
  console.error(err.message);
});

export default sequelize;
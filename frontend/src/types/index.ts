export type Ong = {
  id: number;
  name: string;
  cnpj: string;
  email: string;
  address: string;
  urlImage: string;
  phoneNumber: string;
  actingArea: {
    id: number;
    name: string;
  };
  description: string;
};

export type User = {
  id: number;
  name: string;
  email: string;
  cpf: string;
  ongs: Ong[];
  admin: boolean;
  birthDate?: Date;
  ongEmployeeId?: number;
  sex?: string;
};

export type Vacancy = {
  id: number;
  title: string;
  ong: Ong;
  description: string;
  qtdVacancies: number;
  lastUpdate: Date;
  dateCreated: Date;
};

export type Application = {
  name: string;
  email: string;
  message: string;
};

export type LogSystem = {
  id: number;
  logSystem: string;
  userName: string;
  message: string;
  dateTime: string;
};

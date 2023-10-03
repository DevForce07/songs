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
  id: string;
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

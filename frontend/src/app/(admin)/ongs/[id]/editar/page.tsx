'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { redirect, useParams, useRouter } from 'next/navigation';
import { ChangeEvent, FormEvent, useContext, useEffect, useState } from 'react';
import toast from 'react-hot-toast';
import InputMask from 'react-input-mask';

const actingAreas = [
  {
    id: 1,
    name: 'Saúde',
  },
  {
    id: 2,
    name: 'Educação',
  },
  {
    id: 3,
    name: 'Meio ambiente',
  },
  {
    id: 4,
    name: 'Direitos humanos',
  },
  {
    id: 5,
    name: 'Cultura',
  },
  {
    id: 6,
    name: 'Esporte',
  },
  {
    id: 7,
    name: 'Outros',
  },
];

export default function EditarOng() {
  const { id } = useParams();

  const [ongData, setOngData] = useState({
    name: '',
    email: '',
    cnpj: '',
    urlImage: '',
    address: '',
    phoneNumber: '',
    actingArea: '',
    description: '',
  });

  async function getOng() {
    const { data } = await api.get(`/ongs/findById/${id}`);

    const {
      name,
      email,
      cnpj,
      urlImage,
      address,
      phoneNumber,
      actingArea,
      description,
    } = data;

    setOngData({
      name,
      email,
      cnpj,
      urlImage,
      address,
      phoneNumber,
      actingArea: actingArea.id,
      description,
    });
  }

  useEffect(() => {
    getOng();
  }, []);

  const router = useRouter();

  const { isAuthenticaded, isAuthLoading } = useContext(AuthContext);

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }

  function handleInputChange(
    event: ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) {
    const { id, value } = event.target;

    setOngData({
      ...ongData,
      [id]: value,
    });
  }

  async function handleFormSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const {
      name,
      email,
      cnpj,
      urlImage,
      address,
      phoneNumber,
      actingArea,
      description,
    } = ongData;

    try {
      const response = await api.put('/ongs/update', {
        id,
        name,
        email,
        cnpj,
        urlImage,
        address,
        phoneNumber,
        actingArea,
        description,
      });

      if (response.status === 200) {
        toast.success('ONG editada com sucesso!');
        router.refresh();
        router.push('/ongs');
      }
    } catch (error: any) {
      console.log(error);
      toast.error(error.response.data.details);
    }
  }

  return (
    <div className='max-w-3xl w-full mx-auto'>
      <h1 className='text-3xl my-8 font-bold'>Editar ONG</h1>

      <main className='mb-8'>
        <form className='flex flex-col gap-10' onSubmit={handleFormSubmit}>
          <label htmlFor='name' className='flex flex-col gap-2'>
            Nome da ONG:
            <input
              id='name'
              type='text'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='ONG do Bem'
              value={ongData.name}
              onChange={handleInputChange}
            />
          </label>

          {/* <div className='flex justify-between'>
            <label className='flex flex-col gap-2'>
              Imagem destaque:
              <input type='file' />
            </label>

            <label className='flex flex-col gap-2'>
              Outras imagens (max. 4):
              <input type='file' />
            </label>
          </div> */}

          <label htmlFor='email' className='flex flex-col gap-2'>
            E-mail:
            <input
              id='email'
              type='email'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='nome@email.com'
              value={ongData.email}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='cnpj' className='flex flex-col gap-2'>
            CNPJ:
            <InputMask
              id='cnpj'
              type='text'
              mask='99.999.999/9999-99'
              maskChar={null}
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='xx.xxx.xxx/xxxx-xx'
              value={ongData.cnpj}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='description' className='flex flex-col gap-2'>
            Descrição:
            <textarea
              id='description'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full resize-none h-32'
              placeholder='Descreva aqui sua ONG'
              value={ongData.description}
              onChange={handleInputChange}
            ></textarea>
          </label>

          <label htmlFor='address' className='flex flex-col gap-2'>
            Endereço:
            <input
              id='address'
              type='text'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='Rua das Flores, 123, Centro, Maringá - PR'
              value={ongData.address}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='phoneNumber' className='flex flex-col gap-2'>
            Número de Telefone:
            <InputMask
              mask='(99) 99999-9999'
              maskChar={null}
              id='phoneNumber'
              type='text'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='(xx) xxxxx-xxxx'
              value={ongData.phoneNumber}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='urlImage' className='flex flex-col gap-2'>
            Imagem de capa:
            <input
              id='urlImage'
              type='text'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='https://www.exemplo.com/image.jpg'
              value={ongData.urlImage}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='actingArea' className='flex flex-col gap-2'>
            Campo de atuação:
            <select
              id='actingArea'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              value={ongData.actingArea}
              onChange={handleInputChange}
            >
              <option value='' disabled>
                Selecione
              </option>
              {actingAreas.map((area) => (
                <option key={area.id} value={area.id}>
                  {area.name}
                </option>
              ))}
            </select>
          </label>

          <button className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors'>
            Salvar
          </button>
        </form>
      </main>
    </div>
  );
}

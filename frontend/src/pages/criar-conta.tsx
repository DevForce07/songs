import { Header } from '@/components/Header';

export default function CriarConta() {
  return (
    <>
      <Header />

      <div className='max-w-3xl w-full mx-auto'>
        <h1 className='text-3xl my-8 font-bold'>Crie a sua conta</h1>

        <main className='mb-8'>
          <form className='flex flex-col gap-10'>
            <label htmlFor='name' className='flex flex-col gap-2'>
              Nome da ONG:
              <input
                id='name'
                type='text'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='Ex: ONG do Bem'
              />
            </label>

            <div className='flex'>
              <label className='flex flex-col gap-2'>
                Imagem destaque:
                <input type='file' />
              </label>

              <label className='flex flex-col gap-2'>
                Outras imagens (max. 4):
                <input type='file' />
              </label>
            </div>

            <label htmlFor='description' className='flex flex-col gap-2'>
              Descrição:
              <textarea
                id='description'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full resize-none h-32'
                placeholder='Descreva aqui sua ONG'
              ></textarea>
            </label>

            <label htmlFor='cnpj' className='flex flex-col gap-2'>
              CNPJ:
              <input
                id='cnpj'
                type='text'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='xx.xxx.xxx/xxxx-xx'
              />
            </label>

            <label htmlFor='address' className='flex flex-col gap-2'>
              Endereço:
              <input
                id='address'
                type='text'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='Ex: Rua das Flores, 123, Centro, Maringá - PR'
              />
            </label>

            <label htmlFor='whatsapp' className='flex flex-col gap-2'>
              Número de WhatsApp:
              <input
                id='whatsapp'
                type='text'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='(xx) xxxxx-xxxx'
              />
            </label>

            <label htmlFor='' className='flex flex-col gap-2'>
              Campo de atuação:
              <select className='p-4 rounded border border-neutral-300 bg-transparent w-full'>
                <option value=''>Saúde</option>
                <option value=''>Educação</option>
                <option value=''>Meio ambiente</option>
                <option value=''>Direitos humanos</option>
                <option value=''>Cultura</option>
                <option value=''>Esporte</option>
                <option value=''>Outros</option>
              </select>
            </label>

            <label htmlFor='password' className='flex flex-col gap-2'>
              Escolha uma senha:
              <input
                id='password'
                type='password'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='********'
              />
            </label>

            <label htmlFor='confirm-password' className='flex flex-col gap-2'>
              Confirme a senha:
              <input
                id='confirm-password'
                type='password'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='********'
              />
            </label>

            <button className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors'>
              Criar
            </button>
          </form>
        </main>
      </div>
    </>
  );
}

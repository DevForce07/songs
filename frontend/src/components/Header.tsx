import Link from 'next/link';

export function Header() {
  return (
    <header className='w-full flex justify-between h-24 items-center shadow-sm px-4'>
      <div className='container flex justify-between items-center mx-auto'>
        {/* <h2 className='font-extrabold text-transparent text-8xl bg-clip-text bg-gradient-to-r from-green-500 via-green-500 to-yellow-400'>
          sONGs
        </h2> */}

        <Link href='/'>
          <h2 className='text-2xl font-bold'>
            s<span className='text-cyan-700'>ONG</span>s
          </h2>
        </Link>

        <Link href='/entrar' className='text-cyan-700'>
          Quero criar uma vaga
        </Link>
      </div>
    </header>
  );
}

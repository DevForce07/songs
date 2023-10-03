import { Vacancy } from '@/types';
import Link from 'next/link';

export function VacancyCard({ vacancy }: { vacancy: Vacancy }) {
  return (
    <div className='rounded-lg p-4 border border-neutral-300'>
      <h3 className='text-xl font-bold mb-2' title={vacancy.title}>
        {vacancy.title.length >= 80
          ? vacancy.title.substring(0, 80).concat('...')
          : vacancy.title}
      </h3>
      <p className='text-lg mb-4' title={vacancy.description}>
        {vacancy.description.substring(0, 80).concat('...') || '⠀'}
      </p>

      <Link
        href={`/vaga/${vacancy.id}`}
        className='p-4 py-2 bg-cyan-600 font-bold rounded w-fit text-neutral-50 flex items-center gap-2 hover:bg-cyan-700 transition-colors'
      >
        Ver detalhes
      </Link>
    </div>
  );
}
